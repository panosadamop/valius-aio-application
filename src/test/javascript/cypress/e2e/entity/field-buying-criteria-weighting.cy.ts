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

describe('FieldBuyingCriteriaWeighting e2e test', () => {
  const fieldBuyingCriteriaWeightingPageUrl = '/field-buying-criteria-weighting';
  const fieldBuyingCriteriaWeightingPageUrlPattern = new RegExp('/field-buying-criteria-weighting(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldBuyingCriteriaWeightingSample = {};

  let fieldBuyingCriteriaWeighting;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-buying-criteria-weightings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-buying-criteria-weightings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-buying-criteria-weightings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldBuyingCriteriaWeighting) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-buying-criteria-weightings/${fieldBuyingCriteriaWeighting.id}`,
      }).then(() => {
        fieldBuyingCriteriaWeighting = undefined;
      });
    }
  });

  it('FieldBuyingCriteriaWeightings menu should load FieldBuyingCriteriaWeightings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-buying-criteria-weighting');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldBuyingCriteriaWeighting').should('exist');
    cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
  });

  describe('FieldBuyingCriteriaWeighting page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldBuyingCriteriaWeightingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldBuyingCriteriaWeighting page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-buying-criteria-weighting/new$'));
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-buying-criteria-weightings',
          body: fieldBuyingCriteriaWeightingSample,
        }).then(({ body }) => {
          fieldBuyingCriteriaWeighting = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-buying-criteria-weightings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-buying-criteria-weightings?page=0&size=20>; rel="last",<http://localhost/api/field-buying-criteria-weightings?page=0&size=20>; rel="first"',
              },
              body: [fieldBuyingCriteriaWeighting],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldBuyingCriteriaWeightingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldBuyingCriteriaWeighting page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldBuyingCriteriaWeighting');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
      });

      it('edit button click should load edit FieldBuyingCriteriaWeighting page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
      });

      it('edit button click should load edit FieldBuyingCriteriaWeighting page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldBuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldBuyingCriteriaWeighting', () => {
        cy.intercept('GET', '/api/field-buying-criteria-weightings/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldBuyingCriteriaWeighting').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);

        fieldBuyingCriteriaWeighting = undefined;
      });
    });
  });

  describe('new FieldBuyingCriteriaWeighting page', () => {
    beforeEach(() => {
      cy.visit(`${fieldBuyingCriteriaWeightingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldBuyingCriteriaWeighting');
    });

    it('should create an instance of FieldBuyingCriteriaWeighting', () => {
      cy.get(`[data-cy="buyingCriteriaWeighting"]`).type('bypassing').should('have.value', 'bypassing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldBuyingCriteriaWeighting = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldBuyingCriteriaWeightingPageUrlPattern);
    });
  });
});
