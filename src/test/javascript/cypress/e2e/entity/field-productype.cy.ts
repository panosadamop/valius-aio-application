import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('FieldProductype e2e test', () => {
  const fieldProductypePageUrl = '/field-productype';
  const fieldProductypePageUrlPattern = new RegExp('/field-productype(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldProductypeSample = {};

  let fieldProductype;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-productypes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-productypes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-productypes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldProductype) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-productypes/${fieldProductype.id}`,
      }).then(() => {
        fieldProductype = undefined;
      });
    }
  });

  it('FieldProductypes menu should load FieldProductypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-productype');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldProductype').should('exist');
    cy.url().should('match', fieldProductypePageUrlPattern);
  });

  describe('FieldProductype page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldProductypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldProductype page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-productype/new$'));
        cy.getEntityCreateUpdateHeading('FieldProductype');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldProductypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-productypes',
          body: fieldProductypeSample,
        }).then(({ body }) => {
          fieldProductype = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-productypes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-productypes?page=0&size=20>; rel="last",<http://localhost/api/field-productypes?page=0&size=20>; rel="first"',
              },
              body: [fieldProductype],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldProductypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldProductype page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldProductype');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldProductypePageUrlPattern);
      });

      it('edit button click should load edit FieldProductype page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldProductype');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldProductypePageUrlPattern);
      });

      it('edit button click should load edit FieldProductype page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldProductype');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldProductypePageUrlPattern);
      });

      it('last delete button click should delete instance of FieldProductype', () => {
        cy.intercept('GET', '/api/field-productypes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldProductype').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldProductypePageUrlPattern);

        fieldProductype = undefined;
      });
    });
  });

  describe('new FieldProductype page', () => {
    beforeEach(() => {
      cy.visit(`${fieldProductypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldProductype');
    });

    it('should create an instance of FieldProductype', () => {
      cy.get(`[data-cy="productType"]`).type('Chief Steel Gloves').should('have.value', 'Chief Steel Gloves');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldProductype = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldProductypePageUrlPattern);
    });
  });
});
