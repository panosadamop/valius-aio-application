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

describe('FieldBuyingCriteria e2e test', () => {
  const fieldBuyingCriteriaPageUrl = '/field-buying-criteria';
  const fieldBuyingCriteriaPageUrlPattern = new RegExp('/field-buying-criteria(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldBuyingCriteriaSample = {};

  let fieldBuyingCriteria;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-buying-criteria+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-buying-criteria').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-buying-criteria/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldBuyingCriteria) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-buying-criteria/${fieldBuyingCriteria.id}`,
      }).then(() => {
        fieldBuyingCriteria = undefined;
      });
    }
  });

  it('FieldBuyingCriteria menu should load FieldBuyingCriteria page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-buying-criteria');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldBuyingCriteria').should('exist');
    cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
  });

  describe('FieldBuyingCriteria page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldBuyingCriteriaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldBuyingCriteria page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-buying-criteria/new$'));
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-buying-criteria',
          body: fieldBuyingCriteriaSample,
        }).then(({ body }) => {
          fieldBuyingCriteria = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-buying-criteria+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-buying-criteria?page=0&size=20>; rel="last",<http://localhost/api/field-buying-criteria?page=0&size=20>; rel="first"',
              },
              body: [fieldBuyingCriteria],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldBuyingCriteriaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldBuyingCriteria page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldBuyingCriteria');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
      });

      it('edit button click should load edit FieldBuyingCriteria page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
      });

      it('edit button click should load edit FieldBuyingCriteria page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldBuyingCriteria', () => {
        cy.intercept('GET', '/api/field-buying-criteria/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldBuyingCriteria').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);

        fieldBuyingCriteria = undefined;
      });
    });
  });

  describe('new FieldBuyingCriteria page', () => {
    beforeEach(() => {
      cy.visit(`${fieldBuyingCriteriaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldBuyingCriteria');
    });

    it('should create an instance of FieldBuyingCriteria', () => {
      cy.get(`[data-cy="buyingCriteria"]`).type('mission-critical bypassing').should('have.value', 'mission-critical bypassing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldBuyingCriteria = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldBuyingCriteriaPageUrlPattern);
    });
  });
});
