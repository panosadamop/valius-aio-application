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

describe('FieldAttractivenessFactors e2e test', () => {
  const fieldAttractivenessFactorsPageUrl = '/field-attractiveness-factors';
  const fieldAttractivenessFactorsPageUrlPattern = new RegExp('/field-attractiveness-factors(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldAttractivenessFactorsSample = {};

  let fieldAttractivenessFactors;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-attractiveness-factors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-attractiveness-factors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-attractiveness-factors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldAttractivenessFactors) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-attractiveness-factors/${fieldAttractivenessFactors.id}`,
      }).then(() => {
        fieldAttractivenessFactors = undefined;
      });
    }
  });

  it('FieldAttractivenessFactors menu should load FieldAttractivenessFactors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-attractiveness-factors');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldAttractivenessFactors').should('exist');
    cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
  });

  describe('FieldAttractivenessFactors page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldAttractivenessFactorsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldAttractivenessFactors page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-attractiveness-factors/new$'));
        cy.getEntityCreateUpdateHeading('FieldAttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-attractiveness-factors',
          body: fieldAttractivenessFactorsSample,
        }).then(({ body }) => {
          fieldAttractivenessFactors = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-attractiveness-factors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-attractiveness-factors?page=0&size=20>; rel="last",<http://localhost/api/field-attractiveness-factors?page=0&size=20>; rel="first"',
              },
              body: [fieldAttractivenessFactors],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldAttractivenessFactorsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldAttractivenessFactors page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldAttractivenessFactors');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
      });

      it('edit button click should load edit FieldAttractivenessFactors page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldAttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
      });

      it('edit button click should load edit FieldAttractivenessFactors page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldAttractivenessFactors');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldAttractivenessFactors', () => {
        cy.intercept('GET', '/api/field-attractiveness-factors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldAttractivenessFactors').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);

        fieldAttractivenessFactors = undefined;
      });
    });
  });

  describe('new FieldAttractivenessFactors page', () => {
    beforeEach(() => {
      cy.visit(`${fieldAttractivenessFactorsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldAttractivenessFactors');
    });

    it('should create an instance of FieldAttractivenessFactors', () => {
      cy.get(`[data-cy="attractivenessFactors"]`).type('calculate connect back-end').should('have.value', 'calculate connect back-end');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldAttractivenessFactors = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldAttractivenessFactorsPageUrlPattern);
    });
  });
});
